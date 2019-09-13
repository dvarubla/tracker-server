package ru.aborisov.testtask;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;

/*Главный класс приложения*/
public class Application {
    /*Похоже, другого способа отключить JSP нет*/
    private static class noJSPListener implements LifecycleListener {
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
        }
    }

    private static class NoJSPTomcat extends Tomcat {
        @Override
        public LifecycleListener getDefaultWebXmlListener() {
            return new noJSPListener();
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option portOption = new Option(null, "port", true, "port number");
        portOption.setRequired(true);
        portOption.setType(Number.class);
        options.addOption(portOption);

        Option dbOption = new Option(null, "db", true, "host, port, database, username, password");
        dbOption.setArgs(5);
        options.addOption(dbOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("testtask", options);
            System.exit(1);
        }

        String[] dbArgs = cmd.getOptionValues("db");

        String webAppDirLocation = ".";
        NoJSPTomcat tomcat = new NoJSPTomcat();
        tomcat.setPort(((Number) cmd.getParsedOptionValue("port")).intValue());
        // Без этого не будет работать
        tomcat.getConnector();

        Context ctx = tomcat.addWebapp("", new File(webAppDirLocation).getAbsolutePath());
        /*Передача аргументов командной строки*/
        ctx.addParameter("db.host", dbArgs[0]);
        ctx.addParameter("db.port", dbArgs[1]);
        ctx.addParameter("db.name", dbArgs[2]);
        ctx.addParameter("db.username", dbArgs[3]);
        ctx.addParameter("db.password", dbArgs[4]);
        tomcat.start();
        tomcat.getServer().await();
    }
}
