package dijon.zombiesbase.shooting.recoil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedAttribute;
import org.bukkit.entity.Player;

import java.util.Collections;


public class Recoiler {

    PacketContainer zoomInPacket;
    PacketContainer zoomOutPacket;
    PacketContainer adsPacket;
    PacketContainer adsZoomInPacket;

    Player p;

    public Recoiler(Player p){
        this.p = p;

        initializeZooms();
        initializeADSZooms();

    }

    public void zoomOut(){
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, zoomOutPacket);
    }

    public void normalZoom(){
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, zoomInPacket);
    }

    public void normalZoomADS(){
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, adsPacket);
    }

    public void zoomOutADS(){
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, adsZoomInPacket);
    }


    private void initializeZooms(){


        zoomOutPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.UPDATE_ATTRIBUTES);
        zoomOutPacket.getIntegers().write(0, p.getEntityId());

        WrappedAttribute speedAttribute = WrappedAttribute.newBuilder()
                .attributeKey("generic.movement_speed")
                .baseValue(0.13)
                .build();

        zoomOutPacket.getAttributeCollectionModifier().write(0, Collections.singletonList(speedAttribute));

        zoomInPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.UPDATE_ATTRIBUTES);
        zoomInPacket.getIntegers().write(0, p.getEntityId());

        WrappedAttribute normalAttribute = WrappedAttribute.newBuilder()
                .attributeKey("generic.movement_speed")
                .baseValue(0.1)
                .build();

        zoomInPacket.getAttributeCollectionModifier().write(0, Collections.singletonList(normalAttribute));
    }

    private void initializeADSZooms(){
        adsPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.UPDATE_ATTRIBUTES);
        adsPacket.getIntegers().write(0, p.getEntityId());

        WrappedAttribute adsAttribute = WrappedAttribute.newBuilder()
                .attributeKey("generic.movement_speed")
                .baseValue(0.01)
                .build();

        adsPacket.getAttributeCollectionModifier().write(0, Collections.singletonList(adsAttribute));

        adsZoomInPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.UPDATE_ATTRIBUTES);
        adsZoomInPacket.getIntegers().write(0, p.getEntityId());

        WrappedAttribute adsZoomAttribute = WrappedAttribute.newBuilder()
                .attributeKey("generic.movement_speed")
                .baseValue(0.003)
                .build();

        adsZoomInPacket.getAttributeCollectionModifier().write(0, Collections.singletonList(adsZoomAttribute));
    }

}
