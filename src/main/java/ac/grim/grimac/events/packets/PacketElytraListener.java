package ac.grim.grimac.events.packets;

import ac.grim.grimac.GrimAC;
import ac.grim.grimac.player.GrimPlayer;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketListenerDynamic;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.event.priority.PacketEventPriority;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.out.entitymetadata.WrappedPacketOutEntityMetadata;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import io.github.retrooper.packetevents.utils.player.ClientVersion;

public class PacketElytraListener extends PacketListenerDynamic {
    public PacketElytraListener() {
        super(PacketEventPriority.MONITOR);
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent event) {
        byte packetID = event.getPacketId();

        if (packetID == PacketType.Play.Server.ENTITY_METADATA) {
            WrappedPacketOutEntityMetadata entityMetadata = new WrappedPacketOutEntityMetadata(event.getNMSPacket());
            if (entityMetadata.getEntityId() == event.getPlayer().getEntityId()) {
                GrimPlayer player = GrimAC.playerGrimHashMap.get(event.getPlayer());
                Object zeroBitField = entityMetadata.getWatchableObjects().get(0).getRawValue();

                // It looks like this field is the only one that uses a byte, should be safe
                if (zeroBitField instanceof Byte) {
                    byte field = (byte) zeroBitField;
                    boolean isGliding = (field >> 7 & 1) == 1 && player.getClientVersion().isNewerThanOrEquals(ClientVersion.v_1_9);

                    player.compensatedElytra.lagCompensatedIsGlidingMap.put(player.lastTransactionSent.get(), isGliding);

                    // Send transaction then this packet, doesn't matter which order too much if not sandwiching
                    PacketEvents.get().getPlayerUtils().sendPacket(player.bukkitPlayer, new WrappedPacketOutTransaction(0, player.getNextTransactionID(), false));
                }
            }
        }
    }
}