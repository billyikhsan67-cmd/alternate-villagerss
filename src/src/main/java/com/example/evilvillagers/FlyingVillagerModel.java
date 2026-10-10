package com.example.evilvillagers;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class FlyingVillagerModel extends VillagerModel<EvilVillagerEntity> {
    private final ModelPart arms;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public FlyingVillagerModel(ModelPart root) {
        super(root);
        this.arms = root.getChild("arms");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    @Override
    public void setupAnim(EvilVillagerEntity e, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (e.isAggressive()) {
            this.arms.xRot = -1.6F + Mth.sin(ageInTicks * 0.35F) * 0.12F;
        } else {
            this.arms.xRot = -0.75F + Mth.sin(ageInTicks * 0.1F) * 0.05F;
        }

        float sway = Mth.sin(ageInTicks * 0.12F) * 0.12F;
        this.rightLeg.xRot = 0.4F + sway;
        this.leftLeg.xRot = 0.4F + Mth.sin(ageInTicks * 0.12F + 1.2F) * 0.12F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
    }
}
