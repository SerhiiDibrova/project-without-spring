package com.spring;

public class GameRegister {

  @Inject private Announcer announcer;
  @Inject private Player player;

  public void start() {
    announcer.announce(getClass() + " we are pleased to announce that registration");
    player.register();
    endRegistration();
    announcer.announce("<===== " + getClass() + " END GAMES =====>");
  }

  private void endRegistration() {
    System.out.println(getClass().getName() + " end registration!!!");
  }
}
