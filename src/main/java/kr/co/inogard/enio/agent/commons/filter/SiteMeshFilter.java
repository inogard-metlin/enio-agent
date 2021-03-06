package kr.co.inogard.enio.agent.commons.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

  @Override
  protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
    builder.addTagRuleBundle(new Sm2TagRuleBundle())
        .addDecoratorPath("/*", "/WEB-INF/views/decorators/admin-decorator.jsp")
        .addDecoratorPath("/login", "/WEB-INF/views/decorators/none-decorator.jsp")
        .addDecoratorPath("/error/*", "/WEB-INF/views/decorators/none-decorator.jsp");
  }
}
