package com.sas.agent.com.sas.agent.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.language.Bean;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestApiForSegmentRoutes extends RouteBuilder {

    @Value("${outputDirectory}")
    private String outputDirectory;

    @Override
    public void configure() {

        CsvDataFormat csv = new CsvDataFormat();
        restConfiguration()
                //.contextPath("/rest")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Segment Integration API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                //.port(59191)
                .enableCORS(true);


        rest("/events").description("Segment Integration API")
                .consumes("application/json").produces("application/json")
                .post("/identity").description("Segment Integration API for Identity")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:identity")
                .post("/track").description("Segment Integration API for Track")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:track")
                .post("/alias").description("Segment Integration API for Alias")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:alias")
                .post("/group").description("Segment Integration API for Group")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:group")
                .post("/page").description("Segment Integration API for Page")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:page")
                .post("/screen").description("Segment Integration API for screen")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:screen")
                .post("/authentication").description("Segment Integration API for Authentication")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:authentication")
                .post("/millicon").description("Segment Integration API for Identity")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .toD("direct:millicon");


        rest("/say")
                .get("/hello").toD("direct:hello")
                .get("/bye").consumes("application/json").toD("direct:bye")
                .post("/bye").toD("mock:update");

        from("direct:hello")
                .transform().constant("{'text': 'Hello World'");
        from("direct:bye")
                .transform().constant("Bye World");

        from("direct:identity")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=identity.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=identity.txt&fileExist=Append");


        //from("direct:identity")
        //        .log("Segment Event message: ${body}") .marshal(csv).convertBodyTo(String.class).toD("file://C:/tmp?fileName=test.txt&fileExist=Append");
        from("direct:track")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=track.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=track.txt&fileExist=Append");


        from("direct:alias")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=alias.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=alias.txt&fileExist=Append");

        from("direct:group")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=group.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=group.txt&fileExist=Append");


        from("direct:page")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=page.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=page.txt&fileExist=Append");

        from("direct:screen")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=screen.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=screen.txt&fileExist=Append");

        from("direct:authentication")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=authentication.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=authentication.txt&fileExist=Append");

        from("direct:millicon")
                .log("Segment Event message: ${body}").marshal().json(JsonLibrary.Jackson).transform(body().append("\n")).toD(outputDirectory +"?fileName=millicon.json&fileExist=Append").transform(method("JsonToCsvConverter", "convertJsonToCsvLine(${body})")).toD(outputDirectory +"?fileName=millicon.txt&fileExist=Append");


        //from("direct:route1").log("CI Event message: ${body}").transform(method("transformer", "transformJson(${body})")).log("CI Map: ${body}").toD("dfESP://192.168.99.100:55555/CaptureDigitalBehavior4/digital_stream/digital_event_src?producerDefaultOpcode=eo_INSERT&bodyType=event").routeId("Converting CI360 Events");

    }


}
