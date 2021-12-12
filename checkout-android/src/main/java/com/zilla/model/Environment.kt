package com.zilla.model

enum class Environment(var url: String) {
    LIVE("https://bnpl-gateway.zilla.africa/bnpl/"),
    DEV("https://bnpl-gateway-dev.zilla.africa/bnpl/"),
}