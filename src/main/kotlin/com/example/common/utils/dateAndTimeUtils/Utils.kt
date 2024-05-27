package com.example.common.utils.dateAndTimeUtils

object Utils {
    data class UrlComponents(
        val protocol: String,
        val host: String,
        val port: String,
        val path: String
    )


    fun extractUrlComponents(urlString: String): UrlComponents {
        // Regular expression to match the URL components
        val regex = """(http|https)://([a-zA-Z0-9.\-]+)(:(\d+))?(/?.*)""".toRegex()
        val matchResult = regex.matchEntire(urlString)

        if (matchResult != null) {
            val (protocol, host, _, port, path) = matchResult.destructured
            return UrlComponents(
                protocol = protocol,
                host = host,
                port = if (port.isEmpty()) "80" else port,
                path = path.removeSuffix("/").removeRange(0..0)
            )
        } else {
            return  UrlComponents("","","","")
        }
    }


}



/*
fun main() {
    val url = "http://192.46.236.189:8088/pasakamutuku"
    val components = Utils.extractUrlComponents(url)
    println("Protocol: ${components.protocol}")
    println("Host: ${components.host}")
    println("Port: ${components.port}")
    println("Path: ${components.path.removeRange(0..0)}")
}*/
