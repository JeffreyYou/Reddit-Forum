import { Card, Descriptions, Col, Row } from 'antd';
import { SettingOutlined } from '@ant-design/icons';
import styles from './style.module.scss';

import { useEffect } from 'react';
import UserProfileCard from '../../components/user-profile-card';
import { useUserStore } from '../../store/user-store';

const UserProfile = () => {

  const { user, fetchUserProfile} = useUserStore();

  useEffect(() => {
    fetchUserProfile(); // fetch user profile and update it in the store
    console.log(user)
  }, []);

  return (
    <div className={styles.profile_wrapper} >
      <div className={styles.profile}>
        <Row style={{height: '100%'}}>
          <Col span={14} style={{height: '100%'}}>
            <Col span={24} style={{height: '30%'}}><UserProfileCard className={styles.user_card} user={user}/></Col>
            <Col span={24} style={{height: 'calc(70% - 148px)'}}><Card title="User Information" extra={<SettingOutlined />} className={styles.draft_card}></Card></Col>
          </Col>
          <Col span={10}>
            <Card
              title="User Information"
              extra={<SettingOutlined />}
              className= {styles.posts_card}
            >
              <Descriptions title="User Info">
                <Descriptions.Item label="Full Name">{user.firstName} {user.lastName}</Descriptions.Item>
                <Descriptions.Item label="Registration Date">{user.dateJoined}</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
        </Row>

      </div>
    </div>
  )
}

export default UserProfile