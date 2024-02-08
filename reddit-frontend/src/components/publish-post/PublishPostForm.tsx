import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';
import { IEditPostRequest } from '../../store/postdetailinterface';

const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 14 },
};

export const PublishPostForm: React.FC = () => {
    const [form] = Form.useForm();
    const { postdetail, publishPostDetail, savePostDetail } = usePostDetailStore();
    const [method, setMethod] = useState('publish')
    const onPublishPostDetail = async (editPostRequest: IEditPostRequest) => {
        try {
            editPostRequest.postId = postdetail.postId;
            editPostRequest.images = ["iw", "iuwh"];
            editPostRequest.attachments = ["oef", "111", "345"];
            let response;
            if (method == 'publish') {
                response = await publishPostDetail(editPostRequest);
            }
            else {
                response = await savePostDetail(editPostRequest);
            }
            console.log("Post ID after editing the post:  " + response.postId);
        }
        catch (error) {
            message.error('An error occurred while editing a post');
        }
    };

    const setSave = () => {
        setMethod('save')
    }

    const setPublish = () => {
        setMethod('publish')
    }

    return (
        <Form
            name="user_profile_email_edit"
            onFinish={onPublishPostDetail}
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
                <Button type="primary" htmlType="submit" onClick={setPublish} name="publish">
                    Publish
                </Button>
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit" onClick={setSave} name="save">
                    Save for later
                </Button>
            </Form.Item>
        </Form>
    );
};

export default PublishPostForm;
