package com.github.mysterix5.template.statistics;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {
    public RegisterEvent(final Object source) {
        super(source);
    }
}
