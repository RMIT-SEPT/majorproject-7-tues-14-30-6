import React from 'react';
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16'
import Signup from './components/Signup';

Enzyme.configure({ adapter: new Adapter() });

describe('Signup', () => {
  it('should show register text', () => {
    const wrapper = shallow(<Signup />);
    const text = wrapper.find('div div div div');
    expect(text.find('h5').text()).toBe('Register an account');
  });
})