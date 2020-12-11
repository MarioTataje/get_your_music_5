package com.get_your_music_5.users_system.services

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(entityName: String?, fieldName: String?, fieldValue: Any?) :
        RuntimeException(String
                .format("$entityName not found with the given $fieldName, the given value was $fieldValue"))
