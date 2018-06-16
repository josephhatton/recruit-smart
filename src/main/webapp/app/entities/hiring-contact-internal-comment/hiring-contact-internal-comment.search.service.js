(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('HiringContactInternalCommentSearch', HiringContactInternalCommentSearch);

  HiringContactInternalCommentSearch.$inject = ['$resource'];

  function HiringContactInternalCommentSearch($resource) {
    var resourceUrl = 'api/_search/hiring-contact-internal-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
