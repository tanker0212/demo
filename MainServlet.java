
package com.yourorg.yourldap.opencensus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.opencensus.common.Scope;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;

@SuppressWarnings("serial")
@WebServlet(name = "hellohenry", value = "/" )
public class MainServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(MainServlet.class.getName());
  private static final String projectId = System.getenv("PROJECT");
  private static final Tracer tracer = Tracing.getTracer();

  public void init() throws ServletException {
    try {
      StackdriverTraceExporter.createAndRegister(StackdriverTraceConfiguration.builder()
    .setProjectId(projectId)
    .build());
    } catch (IOException e) {
      logger.info("[init] IOException");
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    logger.info("[doGet] Entered");
    PrintWriter out = resp.getWriter();
    SpanBuilder spanBuilder=tracer.spanBuilder("yourldap.yourorg.com/opencensus")
    .setRecordEvents(true)
    .setSampler(Samplers.alwaysSample());
    logger.info("[doGet] Entering ScopedSpan");
    try (Scope ss = spanBuilder.startScopedSpan()) {
    
      logger.info("[doGet] Annotating 'HeyHenry'");
      tracer.getCurrentSpan().addAnnotation("HeyHenry");
      out.format("[Java] Hello Henry!\n\n");
      
      logger.info("[doGet] Annotating 'Environment'");
      tracer.getCurrentSpan().addAnnotation("Environment");
      Map<String, String> env = System.getenv();
      for (String envName : env.keySet()) {
        out.format("%s=%s%n", envName, env.get(envName));
      }

      tracer.getCurrentSpan().addAnnotation("Complete");
      logger.info("[doGet] Exiting ScopedSpan");
      
    } catch (Exception e) {
      tracer.getCurrentSpan().addAnnotation("Exception!");
      tracer.getCurrentSpan().setStatus(Status.UNKNOWN);
      logger.severe(e.getMessage());
    }
    logger.info("[doGet] Exited ScopedSpan");

    logger.info("[doGet] Sleeping to ensure spans are exported");
    try {
      Thread.sleep(5100);
    } catch (InterruptedException e) {
      logger.info("[doGet] InterruptedException");
    }
    logger.info("[doGet] Done sleeping");
    logger.info("[doGet] Exited.");
  }

}
