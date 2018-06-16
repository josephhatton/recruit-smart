(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('CompanyInternalCommentSearch', CompanyInternalCommentSearch);

  CompanyInternalCommentSearch.$inject = ['$resource'];

  function CompanyInternalCommentSearch($resource) {
    var resourceUrl = 'api/_search/company-internal-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
