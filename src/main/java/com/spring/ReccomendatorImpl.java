package com.spring;

@Deprecated
public class ReccomendatorImpl implements Reccomendator {

  @Value("gameName")
  private String gameName;

  @RandomPrice private String winCashesPrice;

  public ReccomendatorImpl() {
    System.out.println("reccomendator was created");
  }

  @Override
  public void recommend() {
    System.out.println("game name is : " + gameName);
    System.out.println("Win cahes price is : " + winCashesPrice);
  }
}
