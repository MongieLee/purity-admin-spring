package com.example.demo.valid;

import javax.validation.groups.Default;

public interface TodayNewsModelValid extends Default {
    interface Create extends TodayNewsModelValid {

    }

    interface Update extends TodayNewsModelValid {

    }
}
