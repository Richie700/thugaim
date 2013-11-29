/*
Copyright (C) 2013 Braden Walters

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package info.meoblast001.thugaim.npc;

import android.graphics.PointF;

import info.meoblast001.thugaim.Vehicle;
import info.meoblast001.thugaim.engine.Engine;

public abstract class NPCVehicle extends Vehicle
{
  private static short current_fighter_id = 0;

  public NPCVehicle(Engine engine, int bitmap_resource, float x, float y,
                    float rotation)
  {
    super(engine, "npc_" + current_fighter_id++, bitmap_resource, x, y,
          rotation);
  }

  @Override
  protected void rotate(float rotation, long millisecond_delta)
  {
    if (rotation >= 4.0f)
      rotation = 4.0f;
    super.rotate(rotation, millisecond_delta);
  }

  protected void seek(PointF target, long millisecond_delta)
  {
    rotate(
      crossProduct(getRotationUnitVector(),
                   getUnitVectorToTarget(getPosition(), target)) * 8.0f,
      millisecond_delta);
  }

  protected void pursue(PointF target, float rotation, long millisecond_delta)
  {
    float radian_rotation = (float) (rotation * (Math.PI / 180.0f));
    target = new PointF(target.x + (float) Math.sin(rotation),
                        target.y + (float) -Math.cos(rotation));
    seek(target, millisecond_delta);
  }

  protected void flee(PointF target, long millisecond_delta)
  {
    rotate(
      crossProduct(getRotationUnitVector(),
                   getUnitVectorToTarget(target, getPosition())) * 8.0f,
      millisecond_delta);
  }

  protected void evade(PointF target, float rotation, long millisecond_delta)
  {
    float radian_rotation = (float) (rotation * (Math.PI / 180.0f));
    target = new PointF(target.x + (float) Math.sin(rotation),
                        target.y + (float) -Math.cos(rotation));
    flee(target, millisecond_delta);
  }
}
