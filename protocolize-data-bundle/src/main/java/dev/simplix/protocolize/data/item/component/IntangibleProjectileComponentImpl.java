package dev.simplix.protocolize.data.item.component;

import dev.simplix.protocolize.api.item.component.IntangibleProjectileComponent;
import dev.simplix.protocolize.api.item.component.StructuredComponentType;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.data.util.NamedBinaryTagUtil;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.querz.nbt.tag.CompoundTag;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_20_5;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_LATEST;

@Data
@AllArgsConstructor
public class IntangibleProjectileComponentImpl implements IntangibleProjectileComponent {

    private CompoundTag data;

    @Override
    public void read(ByteBuf byteBuf, int protocolVersion) throws IOException{
        data = (CompoundTag) NamedBinaryTagUtil.readTag(byteBuf, protocolVersion);
    }

    @Override
    public void write(ByteBuf byteBuf, int protocolVersion) throws IOException {
        NamedBinaryTagUtil.writeTag(byteBuf, data, protocolVersion);
    }

    @Override
    public StructuredComponentType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements StructuredComponentType<IntangibleProjectileComponent>, Factory {

        public static Type INSTANCE = new Type();

        private static final List<ProtocolIdMapping> MAPPINGS = Arrays.asList(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_20_5, MINECRAFT_LATEST, 19)
        );

        @Override
        public String getName() {
            return "minecraft:intangible_projectile";
        }

        @Override
        public List<ProtocolIdMapping> getMappings() {
            return MAPPINGS;
        }

        @Override
        public IntangibleProjectileComponent createEmpty() {
            return new IntangibleProjectileComponentImpl(null);
        }

        @Override
        public IntangibleProjectileComponent create(CompoundTag compoundTag) {
            return new IntangibleProjectileComponentImpl(compoundTag);
        }
    }

}
