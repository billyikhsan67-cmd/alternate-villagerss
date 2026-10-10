package com.example.evilvillagers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EvilVillagerRenderer extends MobRenderer<EvilVillagerEntity, FlyingVillagerModel> {
    private static ResourceLocation tex(String path) {
        return ResourceLocation.withDefaultNamespace("textures/entity/villager/" + path + ".png");
    }

    private static final ResourceLocation[] BASE = {
            tex("villager"), tex("villager"), tex("villager"), tex("villager"),
            tex("type/desert"), tex("type/savanna"), tex("type/swamp")
    };
    private static final ResourceLocation[] OUTFIT = {
            null, tex("profession/armorer"), tex("profession/weaponsmith"), tex("profession/cleric"),
            null, null, tex("profession/butcher")
    };
    private static final ResourceLocation[] FLOWER = new ResourceLocation[EvilVillagerEntity.VARIANT_COUNT];

    static {
        for (int i = 0; i < FLOWER.length; i++) {
            FLOWER[i] = ResourceLocation.fromNamespaceAndPath(
                    EvilVillagersMod.MOD_ID, "textures/entity/flower_" + i + ".png");
        }
    }

    public EvilVillagerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FlyingVillagerModel(ctx.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
        this.addLayer(new Overlay(this, OUTFIT));
        this.addLayer(new Overlay(this, FLOWER));
    }

    @Override
    public ResourceLocation getTextureLocation(EvilVillagerEntity e) {
        return BASE[e.getVariant()];
    }

    @Override
    protected void setupRotations(EvilVillagerEntity e, PoseStack pose, float bob,
                                  float yBodyRot, float partialTick, float scale) {
        super.setupRotations(e, pose, bob, yBodyRot, partialTick, scale);
        pose.translate(0.0F, Mth.sin(bob * 0.12F) * 0.06F, 0.0F);
        pose.translate(0.0F, 1.0F, 0.0F);
        pose.mulPose(Axis.XP.rotationDegrees(-e.getLean(partialTick)));
        pose.translate(0.0F, -1.0F, 0.0F);
    }

    private static class Overlay extends RenderLayer<EvilVillagerEntity, FlyingVillagerModel> {
        private final ResourceLocation[] textures;

        Overlay(RenderLayerParent<EvilVillagerEntity, FlyingVillagerModel> parent, ResourceLocation[] textures) {
            super(parent);
            this.textures = textures;
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buf, int light, EvilVillagerEntity e,
                           float limbSwing, float limbSwingAmount, float partialTick,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation t = textures[e.getVariant()];
            if (t != null && !e.isInvisible()) {
                renderColoredCutoutModel(getParentModel(), t, pose, buf, light, e, -1);
            }
        }
    }
}
