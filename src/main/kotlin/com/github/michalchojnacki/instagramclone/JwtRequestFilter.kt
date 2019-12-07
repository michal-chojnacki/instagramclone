package com.github.michalchojnacki.instagramclone

import com.github.michalchojnacki.instagramclone.domain.authentication.JwtTokenUseCases
import com.github.michalchojnacki.instagramclone.domain.authentication.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val TOKEN_PREFIX = "Bearer "

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var userDetailsService: AppUserDetailsService
    @Autowired
    private lateinit var jwtTokenUseCases: JwtTokenUseCases

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwt: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            jwt = authorizationHeader.substring(TOKEN_PREFIX.length)
            username = jwtTokenUseCases.extractUsername(jwt)
        }
        if (jwt != null && username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtTokenUseCases.validateToken(jwt, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}