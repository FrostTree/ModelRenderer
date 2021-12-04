package engine.scene.mesh.anim;

import engine.system.Terminate;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;

public class AnimationReader
{
    public static void readAnimation(String animationPath, ArrayList<Integer> jointsID, ArrayList<Float> weights, ArrayList<Animation> animations, ArrayList<Matrix4f> bindPoseTransforms, int vertexAmount)
    {
        AIScene scene = Assimp.aiImportFile(animationPath, Assimp.aiProcess_LimitBoneWeights);
        AINode rootNode = scene.mRootNode();

        PointerBuffer meshes = scene.mMeshes();
        for(int i = 0; i < meshes.limit(); i++)
        {
            AIMesh mesh = AIMesh.create(meshes.get(i));

            Joint[] joints = getJoints(mesh);
            AINode rootJoint = findRootJoint(joints, rootNode);
            if(rootJoint == null) Terminate.stopWithMessage("No root joint detected.");
            processVertexWeights(joints, jointsID, weights, vertexAmount);

            Matrix4f identityMatrix = new Matrix4f().identity();
            computeBindPose(bindPoseTransforms, joints, rootJoint, null);
            processAnimations(joints, scene, animations, bindPoseTransforms);
        }

        Assimp.aiReleaseImport(scene);
    }

    private static Joint[] getJoints(AIMesh mesh)
    {
        Joint[] joints = new Joint[mesh.mNumBones()];

        PointerBuffer bones = mesh.mBones();
        for(int i = 0; i < bones.limit(); i++)
        {
            AIBone bone = AIBone.create(bones.get(i));

            int boneID = i;
            String name = bone.mName().dataString();
            Matrix4f offsetTransform = getMatrix(bone.mOffsetMatrix());

            int[] vertexID = new int[bone.mNumWeights()];
            float[] weights = new float[bone.mNumWeights()];

            AIVertexWeight.Buffer vertexWeights = bone.mWeights();
            for(int w = 0; w < bone.mNumWeights(); w++)
            {
                AIVertexWeight vertexWeight = vertexWeights.get(w);
                vertexID[w] = vertexWeight.mVertexId();
                weights[w] = vertexWeight.mWeight();
            }
            joints[i] = new Joint(boneID, name, offsetTransform, vertexID, weights);
        }
        return joints;
    }

    private static Matrix4f getMatrix(AIMatrix4x4 aiMatrix)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.m00(aiMatrix.a1());
        matrix.m10(aiMatrix.b1());
        matrix.m20(aiMatrix.c1());
        matrix.m30(aiMatrix.d1());

        matrix.m01(aiMatrix.a2());
        matrix.m11(aiMatrix.b2());
        matrix.m21(aiMatrix.c2());
        matrix.m31(aiMatrix.d2());

        matrix.m02(aiMatrix.a3());
        matrix.m12(aiMatrix.b3());
        matrix.m22(aiMatrix.c3());
        matrix.m32(aiMatrix.d3());

        matrix.m03(aiMatrix.a4());
        matrix.m13(aiMatrix.b4());
        matrix.m23(aiMatrix.c4());
        matrix.m33(aiMatrix.d4());

        return matrix;
    }

    private static void computeBindPose(ArrayList<Matrix4f> bindPoseTransforms, Joint[] joints, AINode node, Matrix4f parentTransform)
    {
        Joint currentJoint = findJoint(joints, node.mName().dataString());
        if(currentJoint == null) Terminate.stopWithMessage("One of the joints was null during recursive matrix searching.");
        bindPoseTransforms.add(currentJoint.getIndex(), getMatrix(node.mTransformation()).invertAffine());

        PointerBuffer children = node.mChildren();
        for(int i = 0; i < children.limit(); i++)
        {
            AINode childNode = AINode.create(children.get(i));
            computeBindPose(bindPoseTransforms, joints, childNode, null);
        }
    }

    private static void computeBindPose2(ArrayList<Matrix4f> bindPoseTransforms, Joint[] joints, AINode node, Matrix4f parentTransform)
    {
        Matrix4f currentTransform;
        if(parentTransform == null)
        {
            currentTransform = getMatrix(node.mTransformation());
            Joint currentJoint = findJoint(joints, node.mName().dataString());
            Matrix4f finalMatrix = new Matrix4f(currentTransform);
            bindPoseTransforms.add(currentJoint.getIndex(), finalMatrix.invertAffine());
        }
        else{
                currentTransform = parentTransform;
                Joint currentJoint = findJoint(joints, node.mName().dataString());
                currentTransform.mul(currentJoint.getOffsetTransformation());
                Matrix4f finalMatrix = new Matrix4f(currentTransform);
                bindPoseTransforms.add(currentJoint.getIndex(), finalMatrix.invertAffine());
            }

        PointerBuffer children = node.mChildren();
        for(int i = 0; i < children.limit(); i++)
        {
            AINode childNode = AINode.create(children.get(i));
            computeBindPose2(bindPoseTransforms, joints, childNode, currentTransform);
        }
    }

    private static Joint findJoint(Joint[] joints, String name)
    {
        for(int i = 0; i < joints.length; i++)
        {
            if(name.equals(joints[i].getName())) return joints[i];
        }
        return null;
    }

    private static AINode findRootJoint(Joint[] joints, AINode node)
    {
        if(searchName(joints, node.mName().dataString())) return node;
        PointerBuffer children = node.mChildren();

        for(int i = 0; i < children.limit(); i++)
        {
            AINode childNode = AINode.create(children.get(i));
            AINode root = findRootJoint(joints, childNode);
            if(root != null) return root;
        }
        return null;
    }

    private static boolean searchName(Joint[] joints, String name)
    {
        for(int i = 0; i < joints.length; i++)
        {
            if(name.equals(joints[i].getName())) return true;
        }
        return false;
    }

    private static void processVertexWeights(Joint[] joints, ArrayList<Integer> jointID, ArrayList<Float> weights, int vertexAmount)
    {
        VertexWeight[] vertexWeights = new VertexWeight[vertexAmount];
        for(int i = 0; i < vertexWeights.length; i++)
        {
            vertexWeights[i] = new VertexWeight(i);
        }

        for(int i = 0; i < joints.length; i++)
        {
            Joint currentJoint = joints[i];
            int[] vID = currentJoint.getVertexID();
            float[] vWeights = currentJoint.getWeights();

            for(int v = 0; v < vID.length; v++)
            {
                vertexWeights[vID[v]].append(currentJoint.getIndex(), vWeights[v]);
            }
        }

        for(int i = 0; i < vertexWeights.length; i++)
        {
            VertexWeight currentVertexWeight = vertexWeights[i];

            jointID.add(currentVertexWeight.getJointOne());
            jointID.add(currentVertexWeight.getJointTwo());
            jointID.add(currentVertexWeight.getJointThree());
            jointID.add(currentVertexWeight.getJointFour());

            weights.add(currentVertexWeight.getWeightOne());
            weights.add(currentVertexWeight.getWeightTwo());
            weights.add(currentVertexWeight.getWeightThree());
            weights.add(currentVertexWeight.getWeightFour());
        }
    }

    private static void processAnimations(Joint[] joints, AIScene scene, ArrayList<Animation> animations,ArrayList<Matrix4f> bindPoseTransforms)
    {
        PointerBuffer anims = scene.mAnimations();
        for(int i = 0; i < anims.limit(); i++)
        {
            AIAnimation animation = AIAnimation.create(anims.get(i));

            double duration = animation.mDuration();
            double tps = animation.mTicksPerSecond();
            String name = animation.mName().dataString();

            PointerBuffer keyFrames = animation.mChannels();
            for(int frame = 0; frame < keyFrames.limit(); frame++)
            {
                AINodeAnim keyFrame = AINodeAnim.create(keyFrames.get(frame));
                processKeyFrame(keyFrame, joints, bindPoseTransforms);
            }
        }
    }

    private static void processKeyFrame(AINodeAnim keyFrame, Joint[] joints, ArrayList<Matrix4f> bindPoseTransform)
    {
        AIVectorKey.Buffer positions = keyFrame.mPositionKeys();
        AIQuatKey.Buffer rotations = keyFrame.mRotationKeys();
        AIVectorKey.Buffer scales = keyFrame.mScalingKeys();
        //temporary--
        Joint currentJoint = findJoint(joints, keyFrame.mNodeName().dataString());
        AIVectorKey pos = positions.get(150);
        AIVector3D vec = pos.mValue();
        Matrix4f m = new Matrix4f().identity();
        m.translate(vec.x(), vec.y(), vec.z());

        AIQuatKey qautKey = rotations.get(150);
        AIQuaternion q = qautKey.mValue();
        Quaternionf qauternion = new Quaternionf(q.x(), q.y(), q.z(), q.w());
        m.rotate(qauternion);

        Matrix4f old = bindPoseTransform.get(currentJoint.getIndex());
        old.mul(m);
        old.invertAffine();
        bindPoseTransform.set(currentJoint.getIndex(), old);

        int jointAnimAmount = positions.limit();
        for(int i = 0; i < jointAnimAmount; i++)
        {
            processAnimatedJoint(positions.get(i), rotations.get(i), scales.get(i));
        }
    }

    private static void processAnimatedJoint(AIVectorKey position, AIQuatKey rotation, AIVectorKey scale)
    {

    }
}