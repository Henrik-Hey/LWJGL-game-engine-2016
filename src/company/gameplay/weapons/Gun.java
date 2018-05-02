package company.gameplay.weapons;

import company.entities.Camera;
import company.entities.Entity;
import org.lwjgl.util.vector.Vector3f;

public class Gun {

    private Entity weapon;
    private Entity holder;
    private Vector3f offsetFromLockedPoint;
    private float offsetFromOffset;
    private float rotYOffset;
    private Vector3f unlockedPosition;
    private int weaponAmmo;
    private int weaponDamage;
    private float weaponFireRate;
    private boolean isLocked;
    private Vector3f rot;

    public Gun(Entity weapon, Entity holder, float offsetFromOffset, float rotYOffset, Vector3f offsetFromLockedPoint){
        this.weapon = weapon;
        this.holder = holder;
        this.offsetFromOffset = offsetFromOffset;
        this.rotYOffset = rotYOffset;
        this.offsetFromLockedPoint = offsetFromLockedPoint;
        this.rot = new Vector3f(holder.getRotX(), holder.getRotY(), holder.getRotZ());
    }

    public void update(Entity holder, Camera camera){
        weapon.setRotation(new Vector3f(holder.getRotation().x, holder.getRotation().y - 90 + rotYOffset, camera.getPitch()));
        Vector3f position = new Vector3f(
                holder.getPosition().x + (float)(offsetFromLockedPoint.x * Math.sin(Math.toRadians(holder.getRotY())) +
                        offsetFromOffset * Math.sin(Math.toRadians(weapon.getRotY()))),
                holder.getPosition().y + offsetFromLockedPoint.y,
                holder.getPosition().z + (float)(offsetFromLockedPoint.z * Math.cos(Math.toRadians(holder.getRotY())) +
                        offsetFromOffset * Math.cos(Math.toRadians(weapon.getRotY())))
        );
        weapon.setPosition(position);
    }

    public void update(Entity holder){
        weapon.setRotation(new Vector3f(holder.getRotation().x, holder.getRotation().y - 90, weapon.getRotZ()));
        Vector3f position = new Vector3f(
                holder.getPosition().x + (float)(offsetFromLockedPoint.x * Math.sin(Math.toRadians(holder.getRotY())) +
                        offsetFromOffset * Math.sin(Math.toRadians(weapon.getRotY()))),
                holder.getPosition().y + offsetFromLockedPoint.y,
                holder.getPosition().z + (float)(offsetFromLockedPoint.z * Math.cos(Math.toRadians(holder.getRotY())) +
                        offsetFromOffset * Math.cos(Math.toRadians(weapon.getRotY())))
        );
        weapon.setPosition(position);
    }

    public Entity getWeapon() {
        return weapon;
    }

    public void setWeaponAmmo(int ammo){
        this.weaponAmmo = ammo;
    }

    public void setWeaponFireRate(float SPS){
        this.weaponFireRate = SPS;
    }

}
