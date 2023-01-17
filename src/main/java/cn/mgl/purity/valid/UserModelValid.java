package cn.mgl.purity.valid;

import javax.validation.groups.Default;

public interface UserModelValid extends Default {
    interface Register extends UserModelValid {

    }

    interface Create extends UserModelValid {

    }

    interface Update extends UserModelValid {

    }
}
