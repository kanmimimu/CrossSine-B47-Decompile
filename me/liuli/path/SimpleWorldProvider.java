package me.liuli.path;

import java.util.ArrayList;

public class SimpleWorldProvider implements IWorldProvider {
   private final ArrayList walls = new ArrayList();

   public void addWall(Cell cell) {
      this.walls.add(cell);
   }

   public boolean isBlocked(Cell cell) {
      return this.walls.contains(cell);
   }
}
