package me.j360.disboot.base.domain.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.j360.disboot.base.domain.model.BaseDO;

import java.util.List;

/**
 * Package: com.fotoplace.base.result
 * User: min_xu
 * Date: 2017/5/22 下午2:35
 * 说明：
 */

@Data
@NoArgsConstructor
public class ListData<D> extends BaseDO {


    private Integer total;

    private List<D> list;

    public ListData(List<D> list) {
        this(list.size(), list);
    }

    public ListData(Integer total, List<D> list) {
        this.total = total;
        this.list = list;
    }

}
