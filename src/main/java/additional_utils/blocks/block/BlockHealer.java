package additional_utils.blocks.block;

import net.minecraft.client.gui.font.providers.UnihexProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BlockHealer extends Block
{
    public BlockHealer(Properties properties)
    {
        super(properties);
    }

    public static Set<Vec3> sphere(int samples, float radius)
    {
        Set<Vec3> points = new HashSet<>();

        float phi = Mth.PI * (Mth.sqrt(5f) - 1f); // golden angle in rads

        float step = Mth.PI * 2.0f / samples;
        for (float a = 0; a < (Mth.PI * 2.0f); a += step)
        {
            float theta = phi * a;
            float x = Mth.cos(theta) * radius;
            float y = 1f - (a / (samples - 1)) * 2f;
            //float y = 1f - (a / ((Mth.PI * 2.0f) - 1f)) * 2f;
            float z = Mth.sin(theta) * radius;

            Vec3 temp = new Vec3(x, y, z);

            points.add(temp);
        }

        return points;
    }

    public static Set<Vec3> sphere(int samples)
    {
        Set<Vec3> spherePoints = new HashSet<>();
        float phi = Mth.PI * (Mth.sqrt(5f) - 1f); // golden angle in rads

        for (int i = 0; i < samples; i++)
        {
            float y = 1 - ((float) i / (samples - 1)) * 2;
            float radius = Mth.sqrt(1 - y * y);
            float theta = phi * i;
            float x = Mth.cos(theta) * radius;
            float z = Mth.sin(theta) * radius;
            spherePoints.add(new Vec3(x, y, z));
        }
        return spherePoints;
    }

    void draw_sphere(Level level, Player player)
    {
        Set<Vec3> sphereSet = sphere(300, 5f);//sphere(1000);
        Vec3 loc = player.position();

        //if (sphereSet.size() > 1024)
          //  sphereSet.clear();

        for(Vec3 point : sphereSet)
        {
            MinecraftServer server = level.getServer();

            if (server == null)
                continue;

            ServerLevel sl = server.getLevel(level.dimension());

            if (sl == null)
                continue;

            sl.sendParticles(ParticleTypes.FLAME, loc.x + point.x, loc.y + point.y, loc.z + point.z, 2, 0, 0, 0, 0);
            sl.sendParticles(ParticleTypes.ASH, loc.x + point.x, loc.y + point.y, loc.z + point.z, 1, 0, 0, 0, 0);
        }
    }

    void draw_sphere_on_use(Level level, Player player)
    {
        draw_sphere(level, player);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        //if (level != null && player != null) //Move to some helper utils class?
          //  draw_sphere_on_use(level, player);

        if (level.isClientSide())
            return InteractionResult.PASS;

        final float health = player.getHealth();
        final float max_health = player.getMaxHealth();

        if (health <= max_health)
        {
            player.heal(4.f);

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
