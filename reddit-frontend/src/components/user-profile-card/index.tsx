import { Card, Avatar, Divider  } from 'antd';
import { EditOutlined, EllipsisOutlined, MailOutlined } from '@ant-design/icons';
import { Col, Row } from 'antd';
import { format } from 'date-fns';
import styles from './style.module.scss';

import { IUserProfile } from '../../store/interface';
type MyComponentProps = {
    // className?: string;
    user: IUserProfile;
    editProfile: () => void;
    editEmail: () => void;
};

import GirlSvg1 from '../../assets/girl/girl1.svg'
import GirlSvg2 from '../../assets/girl/girl2.svg'
import GirlSvg3 from '../../assets/girl/girl3.svg'
import GirlSvg4 from '../../assets/girl/girl4.svg'
import GirlSvg5 from '../../assets/girl/girl5.svg'
const testSvgUrl = "https://api.dicebear.com/7.x/miniavs/svg?seed=8";

const UserProfileCard: React.FC<MyComponentProps> = ( { user, editProfile, editEmail} ) => {
    return (
        <Card
            className={styles.card_wrapper}
            actions={[
                <div onClick={editProfile} style={{fontSize: '18px'}}>Edit Profile <EditOutlined key="edit" /></div>, 
                <div onClick={editEmail} style={{fontSize: '18px'}}>Change Email <MailOutlined key="email" /></div>
            ]}
        >   
            <Row align="middle">
                <Col span={8}><Avatar size={200} src={user.profileImageURL} /></Col>
                <Col span={16}>
                    <Col span={24} style={{fontSize: '40px'}}>{user.firstName} {user.lastName}</Col>
                    <Divider />
                    <Col span={24}>Date Joined: {format(user.dateJoined, 'PPP')}</Col>

                </Col>
            </Row>
                
        </Card>
    )
}

export default UserProfileCard

