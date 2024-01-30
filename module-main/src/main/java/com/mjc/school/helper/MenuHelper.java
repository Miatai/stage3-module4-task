package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.*;
import static com.mjc.school.helper.Operations.*;
import static com.mjc.school.helper.Operations.GET_COMMENTS_BY_NEWS_ID;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

public class MenuHelper {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Function<Scanner, Command>> operations;
    private final PrintStream printStream;
    private final QueryParamsParser queryParamsParser;

    public MenuHelper(PrintStream printStream, QueryParamsParser queryParamsParser) {
        operations = new HashMap<>();

        operations.put(String.valueOf(GET_ALL_NEWS.getOperationNumber()), this::getNews);
        operations.put(String.valueOf(GET_NEWS_BY_ID.getOperationNumber()), this::getNewsById);
        operations.put(String.valueOf(CREATE_NEWS.getOperationNumber()), this::createNews);
        operations.put(String.valueOf(UPDATE_NEWS.getOperationNumber()), this::updateNews);
        operations.put(String.valueOf(REMOVE_NEWS_BY_ID.getOperationNumber()), this::deleteNews);

        operations.put(String.valueOf(GET_ALL_AUTHORS.getOperationNumber()), this::getAuthors);
        operations.put(String.valueOf(GET_AUTHOR_BY_ID.getOperationNumber()), this::getAuthorById);
        operations.put(String.valueOf(CREATE_AUTHOR.getOperationNumber()), this::createAuthor);
        operations.put(String.valueOf(UPDATE_AUTHOR.getOperationNumber()), this::updateAuthor);
        operations.put(String.valueOf(REMOVE_AUTHOR_BY_ID.getOperationNumber()), this::deleteAuthor);

        operations.put(String.valueOf(GET_ALL_TAGS.getOperationNumber()), this::getTags);
        operations.put(String.valueOf(GET_TAG_BY_ID.getOperationNumber()), this::getTagById);
        operations.put(String.valueOf(CREATE_TAG.getOperationNumber()), this::createTag);
        operations.put(String.valueOf(UPDATE_TAG.getOperationNumber()), this::updateTag);
        operations.put(String.valueOf(REMOVE_TAG_BY_ID.getOperationNumber()), this::deleteTag);

        operations.put(String.valueOf(GET_ALL_COMMENTS.getOperationNumber()), this::getComments);
        operations.put(String.valueOf(GET_COMMENT_BY_ID.getOperationNumber()), this::getCommentById);
        operations.put(String.valueOf(CREATE_COMMENT.getOperationNumber()), this::createComment);
        operations.put(String.valueOf(UPDATE_COMMENT.getOperationNumber()), this::updateComment);
        operations.put(String.valueOf(REMOVE_COMMENT_BY_ID.getOperationNumber()), this::deleteComment);

        operations.put(String.valueOf(GET_AUTHOR_BY_NEWS_ID.getOperationNumber()), this::getAuthorByNewsId);
        operations.put(String.valueOf(GET_TAGS_BY_NEWS_ID.getOperationNumber()), this::getTagsByNewsId);
        operations.put(String.valueOf(GET_COMMENTS_BY_NEWS_ID.getOperationNumber()), this::getCommentsByNewsId);

        operations.put(String.valueOf(GET_NEWS_BY_QUERY_PARAMS.getOperationNumber()), this::getNewsByQueryParams);

        this.printStream = printStream;
        this.queryParamsParser = queryParamsParser;
    }

    public void printMainMenu() {
        printStream.println(NUMBER_OPERATION_ENTER);
        for (Operations operation : Operations.values()) {
            printStream.println(operation.getOperationWithNumber());
        }
    }

    public Command getCommand(String key, Scanner keyboard) {
        return operations.getOrDefault(key, this::getCommandNotFound).apply(keyboard);
    }

    private Command getCommandNotFound(Scanner keyboard) {
        return Command.NOT_FOUND;
    }

    private Command getNews(Scanner keyboard) {
        printStream.println(GET_ALL_NEWS.getOperation());
        return Command.GET_NEWS;
    }

    private Command getNewsById(Scanner keyboard) {
        printStream.println(GET_NEWS_BY_ID.getOperation());
        printStream.println(NEWS_ID_ENTER);
        return Command.builder()
                .operation(GET_NEWS_BY_ID.getOperationNumber())
                .params(Map.of("id", String.valueOf(getKeyboardNumber(NEWS_ID, keyboard))))
                .build();
    }

    private Command createNews(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(CREATE_NEWS.getOperation());
                printStream.println(NEWS_TITLE_ENTER);
                String title = keyboard.nextLine();
                printStream.println(NEWS_CONTENT_ENTER);
                String content = keyboard.nextLine();
                printStream.println(AUTHOR_ID_ENTER);
                long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
                printStream.println(TAGS_IDS_ENTER);
                List<Long> tagsIds = Stream.of(keyboard.nextLine().split(",")).map(Long::parseLong).toList();

                Map<String, Object> body = Map.of("title", title,
                        "content", content,
                        "authorId", authorId,
                        "tagsIds", tagsIds);

                command = Command.builder()
                        .operation(CREATE_NEWS.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command updateNews(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(UPDATE_NEWS.getOperation());
                printStream.println(NEWS_ID_ENTER);
                long newsId = getKeyboardNumber(NEWS_ID, keyboard);
                printStream.println(NEWS_TITLE_ENTER);
                String title = keyboard.nextLine();
                printStream.println(NEWS_CONTENT_ENTER);
                String content = keyboard.nextLine();
                printStream.println(AUTHOR_ID_ENTER);
                long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
                printStream.println(TAGS_IDS_ENTER);
                List<Long> tagsIds = Stream.of(keyboard.nextLine().split(",")).map(Long::parseLong).toList();

                Map<String, Object> body = Map.of("id", Long.toString(newsId),
                        "title", title,
                        "content", content,
                        "authorId", authorId,
                        "tagsIds", tagsIds);

                command = Command.builder()
                        .operation(UPDATE_NEWS.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command deleteNews(Scanner keyboard) {
        printStream.println(REMOVE_NEWS_BY_ID.getOperation());
        printStream.println(NEWS_ID_ENTER);
        return Command.builder()
                .operation(REMOVE_NEWS_BY_ID.getOperationNumber())
                .params(Map.of("id", Long.toString(getKeyboardNumber(NEWS_ID, keyboard))))
                .build();
    }

    private Command getAuthors(Scanner keyboard) {
        printStream.println(GET_ALL_AUTHORS.getOperation());
        return Command.GET_AUTHORS;
    }

    private Command getAuthorById(Scanner keyboard) {
        printStream.println(GET_AUTHOR_BY_ID.getOperation());
        printStream.println(AUTHOR_ID_ENTER);
        return Command.builder()
                .operation(GET_AUTHOR_BY_ID.getOperationNumber())
                .params(Map.of("id", String.valueOf(getKeyboardNumber(AUTHOR_ID, keyboard))))
                .build();
    }

    private Command createAuthor(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(CREATE_AUTHOR.getOperation());
                printStream.println(AUTHOR_NAME_ENTER);
                String name = keyboard.nextLine();

                Map<String, String> body = Map.of("name", name);
                command = Command.builder()
                        .operation(CREATE_AUTHOR.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command updateAuthor(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(UPDATE_AUTHOR.getOperation());
                printStream.println(AUTHOR_ID_ENTER);
                long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
                printStream.println(AUTHOR_NAME_ENTER);
                String name = keyboard.nextLine();

                Map<String, String> body = Map.of("id", Long.toString(authorId), "name", name);
                command = Command.builder()
                        .operation(UPDATE_AUTHOR.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command deleteAuthor(Scanner keyboard) {
        printStream.println(REMOVE_AUTHOR_BY_ID.getOperation());
        printStream.println(AUTHOR_ID_ENTER);
        return Command.builder()
                .operation(REMOVE_AUTHOR_BY_ID.getOperationNumber())
                .params(Map.of("id", Long.toString(getKeyboardNumber(AUTHOR_ID, keyboard))))
                .build();
    }

    private Command getTags(Scanner keyboard) {
        printStream.println(GET_ALL_TAGS.getOperation());
        return Command.GET_TAGS;
    }

    private Command getTagById(Scanner keyboard) {
        printStream.println(GET_TAG_BY_ID.getOperation());
        printStream.println(TAG_ID_ENTER);
        return Command.builder()
                .operation(GET_TAG_BY_ID.getOperationNumber())
                .params(Map.of("id", String.valueOf(getKeyboardNumber(TAG_ID, keyboard))))
                .build();
    }

    private Command createTag(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(CREATE_TAG.getOperation());
                printStream.println(TAG_NAME_ENTER);
                String name = keyboard.nextLine();

                Map<String, String> body = Map.of("name", name);
                command = Command.builder()
                        .operation(CREATE_TAG.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command updateTag(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(UPDATE_TAG.getOperation());
                printStream.println(TAG_ID_ENTER);
                long tagId = getKeyboardNumber(TAG_ID, keyboard);
                printStream.println(TAG_NAME_ENTER);
                String name = keyboard.nextLine();

                Map<String, String> body = Map.of("id", Long.toString(tagId), "name", name);
                command = Command.builder().operation(UPDATE_TAG.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private Command deleteTag(Scanner keyboard) {
        printStream.println(REMOVE_TAG_BY_ID.getOperation());
        printStream.println(TAG_ID_ENTER);
        return Command.builder()
                .operation(REMOVE_TAG_BY_ID.getOperationNumber())
                .params(Map.of("id", Long.toString(getKeyboardNumber(TAG_ID, keyboard))))
                .build();
    }

    private Command getComments(Scanner keyboard) {
        printStream.println(GET_ALL_COMMENTS.getOperation());
        return Command.GET_COMMENTS;
    }

    private Command getCommentById(Scanner keyboard) {
        printStream.println(GET_COMMENT_BY_ID.getOperation());
        printStream.println(COMMENT_ID_ENTER);
        return Command.builder()
                .operation(GET_COMMENT_BY_ID.getOperationNumber())
                .params(Map.of("id", String.valueOf(getKeyboardNumber(COMMENT_ID, keyboard))))
                .build();
    }

    private Command createComment(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(CREATE_COMMENT.getOperation());
                printStream.println(COMMENT_CONTENT_ENTER);
                String content = keyboard.nextLine();

                printStream.println(NEWS_ID_ENTER);
                long newsId = getKeyboardNumber(NEWS_ID, keyboard);

                Map<String, Object> body = Map.of("content", content,
                        "newsId", Long.toString(newsId));
                command = Command.builder()
                        .operation(CREATE_COMMENT.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }
        return command;
    }

    private Command updateComment(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(UPDATE_COMMENT.getOperation());
                printStream.println(COMMENT_ID_ENTER);
                long commentId = getKeyboardNumber(COMMENT_ID, keyboard);
                printStream.println(COMMENT_CONTENT_ENTER);
                String content = keyboard.nextLine();
                printStream.println(NEWS_ID_ENTER);
                long newsId = getKeyboardNumber(NEWS_ID, keyboard);
                Map<String, Object> body = Map.of("id", Long.toString(commentId),
                        "content", content,
                        "newsId", Long.toString(newsId));

                command = Command.builder()
                        .operation(UPDATE_COMMENT.getOperationNumber())
                        .body(mapper.writeValueAsString(body))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }
        return command;
    }

    private Command deleteComment(Scanner keyboard) {
        printStream.println(REMOVE_COMMENT_BY_ID.getOperation());
        printStream.println(COMMENT_ID_ENTER);
        return Command.builder()
                .operation(REMOVE_COMMENT_BY_ID.getOperationNumber())
                .params(Map.of("id", Long.toString(getKeyboardNumber(COMMENT_ID, keyboard))))
                .build();
    }

    private Command getAuthorByNewsId(Scanner keyboard) {
        printStream.println(GET_AUTHOR_BY_NEWS_ID.getOperation());
        printStream.println(NEWS_ID_ENTER);
        return Command.builder()
                .operation(GET_AUTHOR_BY_NEWS_ID.getOperationNumber())
                .params(Map.of("newsId", Long.toString(getKeyboardNumber(NEWS_ID, keyboard))))
                .build();
    }

    private Command getTagsByNewsId(Scanner keyboard) {
        printStream.println(GET_TAGS_BY_NEWS_ID.getOperation());
        printStream.println(NEWS_ID_ENTER);
        return Command.builder()
                .operation(GET_TAGS_BY_NEWS_ID.getOperationNumber())
                .params(Map.of("newsId", Long.toString(getKeyboardNumber(NEWS_ID, keyboard))))
                .build();
    }

    private Command getCommentsByNewsId(Scanner keyboard) {
        printStream.println(GET_COMMENTS_BY_NEWS_ID.getOperation());
        printStream.println(NEWS_ID_ENTER);
        return Command.builder()
                .operation(GET_COMMENTS_BY_NEWS_ID.getOperationNumber())
                .params(Map.of("newsId", Long.toString(getKeyboardNumber(NEWS_ID, keyboard))))
                .build();
    }

    private Command getNewsByQueryParams(Scanner keyboard) {
        Command command = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                printStream.println(GET_NEWS_BY_QUERY_PARAMS);
                printStream.println(QUERY_PARAMS_ENTER);
                command = Command.builder()
                        .operation(GET_NEWS_BY_QUERY_PARAMS.getOperationNumber())
                        .queryParams(queryParamsParser.parse(keyboard.nextLine()))
                        .build();
                isValid = true;
            } catch (Exception ex) {
                printStream.println(ex.getMessage());
            }
        }

        return command;
    }

    private long getKeyboardNumber(String params, Scanner keyboard) {
        long enter;
        try {
            enter = keyboard.nextLong();
            keyboard.nextLine();
        } catch (Exception ex) {
            keyboard.nextLine();
            throw new RuntimeException(String.format("%s should be number", params));
        }
        return enter;
    }
}