package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */


    public MethodSignature parseFunction(String signatureString) {

        String regexModifier = "(?:(public|protected|private)\\s+)?";
        String regexReturn = "(\\w+)\\s+";
        String regexMethodName = "(\\w+)";
        String regexArguments = "\\((.*?)\\)";

        String regex = regexModifier + regexReturn + regexMethodName + regexArguments;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signatureString);
        if (matcher.find()) {
            String accessModifier = matcher.group(1);
            String returnType = matcher.group(2);
            String methodName = matcher.group(3);
            String argumentsString = matcher.group(4);
            List<MethodSignature.Argument> arguments = new ArrayList<>();
            String argumentRegex = "(\\w+)\\s+(\\w+)";
            Pattern argumentPattern = Pattern.compile(argumentRegex);
            Matcher argumentMatcher = argumentPattern.matcher(argumentsString);

            while (argumentMatcher.find()) {
                String argumentType = argumentMatcher.group(1);
                String argumentName = argumentMatcher.group(2);

                MethodSignature.Argument argument = new MethodSignature.Argument(argumentType, argumentName);
                arguments.add(argument);
            }
            MethodSignature methodSignature = new MethodSignature(methodName, arguments);
            methodSignature.setAccessModifier(accessModifier);
            methodSignature.setReturnType(returnType);

            return methodSignature;
        }

        throw new UnsupportedOperationException("Sad");
    }
}
