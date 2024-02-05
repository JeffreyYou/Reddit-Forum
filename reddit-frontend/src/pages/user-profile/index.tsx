
import styles from './style.module.scss';
import { Card, Avatar, Typography, Descriptions } from 'antd';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Col, Row } from 'antd';

import UserProfileCard from '../../components/user-profile';



const UserProfile = () => {

  // const { firstName, lastName, registrationDate, profileImage } = {
  //   firstName: "John",
  //   lastName: "Doe",
  //   registrationDate: "2020-01-01T12:00:00.000Z",
  //   profileImage: "https://example.com/path/to/profile-image.jpg", // Placeholder image URL
  // };
  const formatDate = (isoString : string) => {
    const date = new Date(isoString);
    return date.toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' });
  };

  return (
    <div className={styles.profile_wrapper} >
      <div className={styles.profile}>
        <Row style={{height: '100%'}}>
          <Col span={14} style={{height: '100%'}}>
            <Col span={24} style={{height: '30%'}}><UserProfileCard className={styles.user_card}/></Col>
            <Col span={24} style={{height: 'calc(70% - 148px)'}}><Card title="User Information" extra={<SettingOutlined />} className={styles.draft_card}></Card></Col>
          </Col>
          <Col span={10}>
            <Card
              title="User Information"
              extra={<SettingOutlined />}
              className= {styles.posts_card}
            >
              <Descriptions title="User Info">
                <Descriptions.Item label="Full Name">John Doe</Descriptions.Item>
                <Descriptions.Item label="Registration Date">January 1, 2020</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
        </Row>

      </div>
    </div>
  )
}

export default UserProfile