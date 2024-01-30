package com.mjc.school.helper;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class QueryParamsParser {

    Map<String, Object> parse(String queryParams) {
        return Arrays.stream(queryParams.split("&"))
            .map(queryParamPair -> queryParamPair.split("="))
            .collect(toMap(queryParamArray -> queryParamArray[0],
                queryParamArray -> queryParamArray[1].contains(",")
                    ? queryParamArray[1].split(",")
                    : queryParamArray[1]));
    }
}
