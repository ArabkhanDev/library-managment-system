package com.company.library.model.base;

import com.company.library.enums.response.ResponseMessages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import static com.company.library.enums.response.SuccessResponseMessages.*;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse <T> {

    HttpStatus status;
    Meta meta;
    T data;

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static final class Meta {
        String key;
        String message;

        public static Meta of(String key, String message) {
            return Meta.builder()
                    .key(key)
                    .message(message)
                    .build();
        }

        public static Meta of(ResponseMessages responseMessages) {
            return of(responseMessages.key(), responseMessages.message());
        }

    }

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status(HttpStatus.OK)
                .data(data)
                .meta(Meta.of(SUCCESS))
                .build();
    }

    public static <T> BaseResponse<T> success() {
        return success(null);
    }

    public static <T> BaseResponse<T> created(T data) {
        return BaseResponse.<T>builder()
                .status(HttpStatus.CREATED)
                .data(data)
                .meta(Meta.of(CREATED))
                .build();
    }
    public static <T> BaseResponse<T> created() {
        return created(null);
    }

    public static <T> BaseResponse<T> notContent() {
        return BaseResponse.<T>builder()
                .status(HttpStatus.NO_CONTENT)
                .meta(Meta.of(NOT_CONTENT))
                .build();
    }

}

