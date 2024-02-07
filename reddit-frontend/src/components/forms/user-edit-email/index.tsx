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



const UserProfileEdit = ( ) => {
  
  const { user, userTemporay, updateUserProfile, updateUserTemporaryProfile } = useUserStore();
  const [loading, setLoading] = useState(false);
  
  const onChangeFirstName = (e: any) => {
    updateUserTemporaryProfile({...userTemporay, firstName: e.target.value})
  }
  const onChangeLastName = (e: any) => {
    updateUserTemporaryProfile({...userTemporay, lastName: e.target.value})
  }
  const onChangeEmail = (e: any) => {
    updateUserTemporaryProfile({...userTemporay, email: e.target.value})
  }


  return (
    <Form

      name="user_profile_edit"
      {...formItemLayout}
      onFinish={onFinish}
      initialValues={{
        'input-number': 3,
        'checkbox-group': ['A', 'B'],
        rate: 3.5,
        'color-picker': null,
      }}
      style={{ maxWidth: 600, margin: '40px 20px', height: '100px', alignContent: 'center' }}
    >
      <Form.Item label="New Email">
        <Input value={userTemporay.email} onChange={onChangeEmail}/>
      </Form.Item>

    </Form>
  )
}

export default UserProfileEdit