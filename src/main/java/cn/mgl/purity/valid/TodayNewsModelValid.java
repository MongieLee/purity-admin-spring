package cn.mgl.purity.valid;

import javax.validation.groups.Default;

public interface TodayNewsModelValid extends Default {
    interface Create extends TodayNewsModelValid {

    }

    interface Update extends TodayNewsModelValid {

    }
}
