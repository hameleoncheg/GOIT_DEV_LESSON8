import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String timezone = request.getParameter("timezone");
        if (timezone != null && !isValidTimezone(timezone)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(400, "Invalid timezone");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    private boolean isValidTimezone(String timezone) {
        try {
            TimeZone.getTimeZone("GMT" + timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
