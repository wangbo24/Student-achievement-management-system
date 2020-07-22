package kevin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页操作
 *  searchText=&sortOrder=asc&pageSize=7&pageNumber=1
 */
@Getter
@Setter
@ToString
public class Page {
    private String searchText;
    private String sortOrder;
    private Integer pageSize;
    private Integer pageNumber;

    private Page(){}
    public static Page parse(HttpServletRequest req){
        Page p = new Page();
        p.searchText = req.getParameter("searchText");
        p.sortOrder = req.getParameter("sortOrder");
        p.pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        p.pageSize = Integer.parseInt(req.getParameter("pageSize"));
        return p;
    }

}
