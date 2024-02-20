package com.booksearch;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public interface RestDocsUtils {

    static OperationRequestPreprocessor requestPreprocessor() {
        return Preprocessors.preprocessRequest(
                Preprocessors.modifyUris()
                        .scheme("http")
                        .host("sonaki.com")
                        .removePort(),
                Preprocessors.prettyPrint()
        );
    }

    static OperationResponsePreprocessor responsePreprocessor() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }
}
