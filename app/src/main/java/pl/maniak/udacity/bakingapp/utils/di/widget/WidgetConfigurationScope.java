package pl.maniak.udacity.bakingapp.utils.di.widget;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface WidgetConfigurationScope {
}