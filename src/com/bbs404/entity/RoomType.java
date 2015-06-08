package com.bbs404.entity;

public enum RoomType {
  Single(0), Group(1);

  int value;
  RoomType(int value){
    this.value=value;
  }

  public int getValue(){
    return value;
  }

  public static RoomType fromInt(int i){
    return values()[i];
  }
}
