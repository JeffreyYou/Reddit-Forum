import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';
import { IEditPostRequest } from '../../store/postdetailinterface';

const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 14 },
};

export const EditPostForm: React.FC = () => {
    const [form] = Form.useForm();
    const { editPostDetail } = usePostDetailStore();
    const onEditPostDetail = async (editPostRequest: IEditPostRequest) => {
        try {
            editPostRequest.postId = null;
            editPostRequest.images = ["iw", "iuwh"];
            editPostRequest.attachments = ["oef", "111", "345"]
            const response = await editPostDetail(editPostRequest);
            console.log("Post ID after editing the post:  " + response.postId);
        }
        catch (error) {
            message.error('An error occurred while editing a post');
        }
    };

    return (
        <Form
            name="user_profile_email_edit"
            onFinish={onEditPostDetail}
            {...formItemLayout}
            style={{ maxWidth: 600 }}
        >
            <Form.Item label="Title" name="title">
                <Input />
            </Form.Item>

            <Form.Item label="Content" name="content">
                <Input />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Edit
                </Button>
            </Form.Item>
        </Form>
    );
};

export default EditPostForm;
