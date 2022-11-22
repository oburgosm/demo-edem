package com.capgemini.edem.dgs.instrumentation;

import java.text.MessageFormat;
import java.util.Locale;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.language.AstPrinter;
import graphql.language.AstTransformer;
import graphql.language.BooleanValue;
import graphql.language.EnumValue;
import graphql.language.Node;
import graphql.language.NodeVisitor;
import graphql.language.NodeVisitorStub;
import graphql.language.NullValue;
import graphql.language.OperationDefinition;
import graphql.language.OperationDefinition.Operation;
import graphql.language.Value;
import graphql.language.VariableReference;
import graphql.schema.DataFetcher;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import graphql.util.TreeTransformerUtil;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
final class SleuthGraphQLInstrumentation extends SimpleInstrumentation {

  // https://github.com/open-telemetry/opentelemetry-specification/blob/main/specification/trace/semantic_conventions/instrumentation/graphql.md
  private static final String OPERATION_NAME = "graphql.operation.name";
  private static final String OPERATION_TYPE = "graphql.operation.type";
  private static final String GRAPHQL_SOURCE = "graphql.source";
  private static final NodeVisitor sanitizingVisitor = new SanitizingVisitor();
  private static final AstTransformer astTransformer = new AstTransformer();
  private final boolean sanitizeQuery;
  private final Tracer tracer;

  SleuthGraphQLInstrumentation(final Tracer tracer) {
    this.tracer = tracer;
    // At the moment, we always sanitize the query.
    this.sanitizeQuery = true;
  }

  @Override
  public InstrumentationState createState() {
    return new SleuthInstrumentationState();
  }

  @Override
  public InstrumentationContext<ExecutionResult> beginExecution(final InstrumentationExecutionParameters parameters) {

    final Span nextSpan = tracer.nextSpan();

    nextSpan.start();

    SleuthInstrumentationState state = parameters.getInstrumentationState();
    state.setContext(nextSpan);

    return SimpleInstrumentationContext.whenCompleted(
            (result, throwable) -> {
              for (final GraphQLError error : result.getErrors()) {

                var errorEvent = getErrorEvent(error);
                nextSpan.tag("error", errorEvent);

              }

              endSpan(nextSpan, state);
            });
  }

  private void endSpan(final Span nextSpan, final SleuthInstrumentationState state) {
    String operationName = state.getOperationName();
    if (operationName != null) {
      nextSpan.tag(OPERATION_NAME, operationName);
    }
    if (state.getOperation() != null) {
      nextSpan.tag(OPERATION_TYPE, state.getOperation().name());
    }
    String query = state.getQuery();
    if (query != null) {
      nextSpan.tag(GRAPHQL_SOURCE, state.getQuery());
    }
    nextSpan.end();
  }

  private static final String EXCEPTION_EVENT_NAME = "exception";
  private static final String EXCEPTION_TYPE = "exception.type";
  private static final String EXCEPTION_MESSAGE = "exception.message";

  private static String getErrorEvent(final GraphQLError error) {
    return MessageFormat.format("{0}'{'\"{1}\" => {2}\"{3}\" => {4}'}'",
            EXCEPTION_EVENT_NAME,
            EXCEPTION_TYPE,
            String.valueOf(error.getErrorType()),
            EXCEPTION_MESSAGE,
            error.getMessage());
  }

  @Override
  public InstrumentationContext<ExecutionResult> beginExecuteOperation(
          final InstrumentationExecuteOperationParameters parameters) {

    SleuthInstrumentationState state = parameters.getInstrumentationState();
    Span span = state.getSpan();

    OperationDefinition operationDefinition
            = parameters.getExecutionContext().getOperationDefinition();
    Operation operation = operationDefinition.getOperation();
    String operationType = operation.name().toLowerCase(Locale.ROOT);
    String operationName = operationDefinition.getName();

    String spanName = operationType;
    if (operationName != null && !operationName.isEmpty()) {
      spanName += " " + operationName;
    }
    span.name(spanName);

    state.setOperation(operation);
    state.setOperationName(operationName);

    Node<?> node = operationDefinition;
    if (sanitizeQuery) {
      node = sanitize(node);
    }
    state.setQuery(AstPrinter.printAst(node));

    return SimpleInstrumentationContext.noOp();
  }

  @Override
  public DataFetcher<?> instrumentDataFetcher(
          final DataFetcher<?> dataFetcher, final InstrumentationFieldFetchParameters parameters) {
    SleuthInstrumentationState state = parameters.getInstrumentationState();

    return (DataFetcher<Object>) environment -> {
      try ( Tracer.SpanInScope ignored = tracer.withSpan(state.getSpan())) {
        return dataFetcher.get(environment);
      }
    };
  }

  private static Node<?> sanitize(final Node<?> node) {
    return astTransformer.transform(node, sanitizingVisitor);
  }

  /**
   * Same code as in graphql-java-12
   * <a href="https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/701ed543117f8942c4e159441c99f7897d5706f0/instrumentation/graphql-java-12.0/library/src/main/java/io/opentelemetry/instrumentation/graphql/OpenTelemetryInstrumentation.java#L139">instrumentation.</a>
   */
  @SuppressWarnings("rawtypes")
  private static class SanitizingVisitor extends NodeVisitorStub {

    @Override
    protected TraversalControl visitValue(final Value<?> node, final TraverserContext<Node> context) {
      // Query input values are always replace by ?
      // Maybe make this configurable ?
      EnumValue newValue = new EnumValue("?");
      return TreeTransformerUtil.changeNode(context, newValue);
    }

    private TraversalControl visitSafeValue(final Value<?> node, final TraverserContext<Node> context) {
      return super.visitValue(node, context);
    }

    @Override
    public TraversalControl visitVariableReference(
            final VariableReference node, final TraverserContext<Node> context) {
      return visitSafeValue(node, context);
    }

    @Override
    public TraversalControl visitBooleanValue(final BooleanValue node, final TraverserContext<Node> context) {
      return visitSafeValue(node, context);
    }

    @Override
    public TraversalControl visitNullValue(final NullValue node, final TraverserContext<Node> context) {
      return visitSafeValue(node, context);
    }
  }

  static final class SleuthInstrumentationState implements InstrumentationState {

    private Span context;
    private Operation operation;
    private String operationName;
    private String query;

    public Span getSpan() {
      return context;
    }

    public void setContext(final Span context) {
      this.context = context;
    }

    Operation getOperation() {
      return operation;
    }

    void setOperation(final Operation operation) {
      this.operation = operation;
    }

    String getOperationName() {
      return operationName;
    }

    void setOperationName(final String operationName) {
      this.operationName = operationName;
    }

    String getQuery() {
      return query;
    }

    void setQuery(final String query) {
      this.query = query;
    }
  }

}
