package me.liuli.path;

import java.util.ArrayList;
import java.util.Collections;

public class Pathfinder {
   public static final Cell[] COMMON_NEIGHBORS = new Cell[]{new Cell(1, 0, 0), new Cell(-1, 0, 0), new Cell(0, 1, 0), new Cell(0, -1, 0), new Cell(0, 0, 1), new Cell(0, 0, -1)};
   public static final Cell[] DIAGONAL_NEIGHBORS = new Cell[]{new Cell(1, 1, 0), new Cell(-1, -1, 0), new Cell(1, -1, 0), new Cell(-1, 1, 0), new Cell(0, 1, 1), new Cell(0, -1, -1), new Cell(0, 1, -1), new Cell(0, -1, 1), new Cell(1, 0, 0), new Cell(-1, 0, 0), new Cell(0, 1, 0), new Cell(0, -1, 0), new Cell(0, 0, 1), new Cell(0, 0, -1)};
   private final Cell start;
   private final Cell end;
   private final Cell[] neighbours;
   private final IWorldProvider world;

   public Pathfinder(Cell start, Cell end, Cell[] neighbours, IWorldProvider world) {
      this.start = start;
      this.end = end;
      this.neighbours = neighbours;
      this.world = world;
   }

   public Cell getStart() {
      return this.start;
   }

   public Cell getEnd() {
      return this.end;
   }

   public Cell[] getNeighbours() {
      return this.neighbours;
   }

   public IWorldProvider getWorld() {
      return this.world;
   }

   public ArrayList findPath() {
      return this.findPath(Integer.MAX_VALUE);
   }

   public ArrayList findPath(int maxLoops) {
      ArrayList<Cell> open = new ArrayList();
      ArrayList<Cell> closed = new ArrayList();
      open.add(this.start);
      Cell current = null;

      for(int loops = 0; !open.isEmpty() && loops < maxLoops; ++loops) {
         current = (Cell)open.get(0);
         int currentIdx = 0;

         for(int i = 1; i < open.size(); ++i) {
            if (((Cell)open.get(i)).f < current.f) {
               current = (Cell)open.get(i);
               currentIdx = i;
            }
         }

         open.remove(currentIdx);
         closed.add(current);
         if (current.equals(this.end)) {
            break;
         }

         ArrayList<Cell> children = new ArrayList();

         for(Cell neighbor : this.neighbours) {
            Cell child = new Cell(current.x + neighbor.x, current.y + neighbor.y, current.z + neighbor.z);
            child.parent = current;
            if (!this.world.isBlocked(child)) {
               children.add(child);
            }
         }

         for(Cell child : children) {
            if (!closed.contains(child)) {
               child.g = current.g + 1;
               child.h = (int)(Math.pow((double)(child.x - this.end.x), (double)2.0F) + Math.pow((double)(child.y - this.end.y), (double)2.0F) + Math.pow((double)(child.z - this.end.z), (double)2.0F));
               child.f = child.g + child.h;
               if (!open.contains(child) || ((Cell)open.get(open.indexOf(child))).g <= child.g) {
                  open.add(child);
               }
            }
         }
      }

      ArrayList<Cell> path = new ArrayList();

      for(Cell cur = current; cur != null; cur = cur.parent) {
         path.add(cur);
      }

      Collections.reverse(path);
      return path;
   }
}
