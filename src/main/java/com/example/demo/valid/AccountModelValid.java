package com.example.demo.valid;

import javax.validation.groups.Default;

public interface AccountModelValid extends Default {
    interface Register extends AccountModelValid {

    }

    interface Login extends AccountModelValid {

    }
}
