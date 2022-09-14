package top.year21.onlineedu.serviceedu.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/22 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {
    private String id;
    private String title;
    //表示一级分类中有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
