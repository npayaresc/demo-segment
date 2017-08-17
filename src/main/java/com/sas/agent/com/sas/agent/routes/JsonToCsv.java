package com.sas.agent.com.sas.agent.routes;

import org.jsontocsv.parser.JSONFlattener;
import org.jsontocsv.writer.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by eurnpa on 25.07.2017.
 */

@Component("JsonToCsvConverter")
public class JsonToCsv {

    private static final Logger logger = LoggerFactory
            .getLogger(JsonToCsv.class);

    public String convertJsonToCsvLine(String body){


        //String [] bodyPieces = body.split("=");

        /*
         *  Parse a JSON String and convert it to CSV
         */
        List<Map<String, String>> flatJson = JSONFlattener.parseJson(body);
        // Using the default separator ','
        //CSVWriter.writeToFile(CSVWriter.getCSV(flatJson), "files/sample_string.csv");
        return CSVWriter.getAsCSVLine(flatJson, ",");
    }
}
