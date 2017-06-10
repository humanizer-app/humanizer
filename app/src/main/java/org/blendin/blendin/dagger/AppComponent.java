package org.blendin.blendin.dagger;

import org.blendin.blendin.MainActivity;
import org.blendin.blendin.auth.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);
}