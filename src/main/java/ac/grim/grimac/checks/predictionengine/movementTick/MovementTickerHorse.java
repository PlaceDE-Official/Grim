package ac.grim.grimac.checks.predictionengine.movementTick;

import ac.grim.grimac.player.GrimPlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.util.Vector;

public class MovementTickerHorse extends MovementTickerLivingVehicle {

    public MovementTickerHorse(GrimPlayer player) {
        super(player);

        AbstractHorse horse = (AbstractHorse) player.playerVehicle;
        player.speed = (float) horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();

        // LivingEntity AIStep
        //player.clientVelocity.multiply(0.98);

        // Setup player inputs
        float f = player.vehicleHorizontal * 0.5F;
        float f1 = player.vehicleForward;

        // TODO: This takes away control of the player when the horse is standing
            /*if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                f = 0.0F;
                f1 = 0.0F;
            }*/

        // TODO: Handle jump
            /*if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
                double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                double d1;
                if (this.hasEffect(Effects.JUMP)) {
                    d1 = d0 + (double) ((float) (this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
                } else {
                    d1 = d0;
                }

                Vector3d vector3d = this.getDeltaMovement();
                this.setDeltaMovement(vector3d.x, d1, vector3d.z);
                this.setIsJumping(true);
                this.hasImpulse = true;
                if (f1 > 0.0F) {
                    float f2 = MathHelper.sin(this.yRot * ((float) Math.PI / 180F));
                    float f3 = MathHelper.cos(this.yRot * ((float) Math.PI / 180F));
                    this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
                }

                this.playerJumpPendingScale = 0.0F;
            }*/

        // More jumping stuff
            /*if (this.onGround) {
                this.playerJumpPendingScale = 0.0F;
                this.setIsJumping(false);
            }*/

        // TODO: Handle if the player has no saddle
     /*else {
            this.flyingSpeed = 0.02F;
            super.travel(inputMovement);
        }*/


        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }

        this.movementInput = new Vector(f, 0, f1);
        if (movementInput.lengthSquared() > 1) movementInput.normalize();
    }
}