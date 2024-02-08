import { InboxOutlined } from '@ant-design/icons';
import { Input, Form, Upload} from 'antd';
import { useUserStore } from '../../../store/user-store';
import { useState } from 'react';

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 14 },
};

const normFile = (e: any) => {
  console.log('Upload event:', e);
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};

const onFinish = (values: any) => {
  console.log('Received values of form: ', values);
};



const UserEmailEdit = ( ) => {
  
  const { user, userTemporay, updateUserProfile, updateUserTemporaryProfile } = useUserStore();
  const [loading, setLoading] = useState(false);
  
  const onChangeFirstName = (e: any) => {
    updateUserTemporaryProfile({...userTemporay, firstName: e.target.value})
  }
  const onChangeLastName = (e: any) => {
    updateUserTemporaryProfile({...userTemporay, lastName: e.target.value})
  }

  return (
    <Form
      name="user_profile_email_edit"
      {...formItemLayout}
      onFinish={onFinish}
      style={{ maxWidth: 600 }}
    >
      <Form.Item label="Email">
        <Input value={userTemporay.email} disabled={true}/>
      </Form.Item>
      <Form.Item label="First Name">
        <Input value={userTemporay.firstName} onChange={onChangeFirstName}/>
      </Form.Item>
      <Form.Item label="Last Name">
        <Input value={userTemporay.lastName} onChange={onChangeLastName}/>
      </Form.Item>

      <Form.Item label="Profile Image">
        <Form.Item name="dragger" valuePropName="fileList" getValueFromEvent={normFile} noStyle>
          <Upload.Dragger name="files" action="/upload.do">
            <p className="ant-upload-drag-icon"><InboxOutlined /></p>
            <p className="ant-upload-text">Click or drag file to this area to upload</p>
          </Upload.Dragger>
        </Form.Item>
      </Form.Item>



    </Form>
  )
}

export default UserEmailEdit