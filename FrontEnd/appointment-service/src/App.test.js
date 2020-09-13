import React from 'react';
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16'
import Signup from './components/Signup';
import Home from './components/Layout/Home';
import Login from './components/Login';

Enzyme.configure({ adapter: new Adapter() });

describe('Signup', () => {
  it('Should show register text', () => {
    const wrapper = shallow(<Signup />);
    const text = wrapper.find('div div div div');
    expect(text.find('h5').text()).toBe('Register an account');
  });

  it('Should show no notification text on the form', () => {
    const wrapper = shallow(<Signup />);
    const text = wrapper.find('div div div div');
    expect(text.find('h6').text()).toBe('');
  });

  it('Test that the home page text is rendered', () => {
    const wrapper = shallow(<Home />);
    const text = wrapper.find('div');
    expect(text.find('h1').first().text()).toBe('AGME');
  });

  it('Test that the Login page text is rendering', () => {
    const wrapper = shallow(<Login />);
    const text = wrapper.find('div div div div h5').first();
    expect(text.text()).toBe('Log in');
  });

})