import { WebscaleUiPage } from './app.po';

describe('webscale-ui App', () => {
  let page: WebscaleUiPage;

  beforeEach(() => {
    page = new WebscaleUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
