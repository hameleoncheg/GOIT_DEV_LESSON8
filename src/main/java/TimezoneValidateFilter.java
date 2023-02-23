import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException{
        String timezone = req.getParameter("timezone");
        try{
            timezone = String.join("+", timezone.split(" "));
        }catch (NullPointerException e){
            timezone = "UTC";
        }
        if (timezone != null && !isValidTimezone(timezone)) {
            resp.sendError(400, "Invalid timezone");
        } else {
            chain.doFilter(req, resp);
        }
    }

    private boolean isValidTimezone(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
