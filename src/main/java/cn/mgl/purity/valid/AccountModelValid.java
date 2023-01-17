package cn.mgl.purity.valid;

import javax.validation.groups.Default;

public interface AccountModelValid extends Default {
    interface Register extends AccountModelValid {

    }

    interface Create extends AccountModelValid {

    }

    interface Login extends AccountModelValid {

    }
}
